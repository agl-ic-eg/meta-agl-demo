# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "Chromium webruntime for webOS"
AUTHOR = "Lokesh Kumar Goel <lokeshkumar.goel@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0 & BSD-3-Clause & LGPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = "\
    file://src/LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d \
    file://src/third_party/blink/renderer/core/LICENSE-LGPL-2;md5=36357ffde2b64ae177b2494445b79d21 \
    file://src/third_party/blink/renderer/core/LICENSE-LGPL-2.1;md5=a778a33ef338abbaf8b8a7c36b6eec80 \
"

require gn-utils.inc

inherit gettext qemu pythonnative

DEPENDS = "virtual/gettext wayland wayland-native pixman freetype glib-2.0 fontconfig openssl pango cairo icu libxkbcommon libexif dbus pciutils udev libcap alsa-lib virtual/egl elfutils-native libdrm atk at-spi2-atk gperf-native gconf nss nss-native nspr nspr-native bison-native qemu-native"

PROVIDES = "${BROWSER_APPLICATION}"

SRCREV_v8 = "1e3af71f1ff3735e8a5b639c48dfca63a7b8a647"

# we don't include SRCPV in PV, so we have to manually include SRCREVs in do_fetch vardeps
do_fetch[vardeps] += "SRCREV_v8"
SRCREV_FORMAT = "main_v8"

S = "${WORKDIR}/git"

SRC_DIR = "${S}/src"
BUILD_TYPE = "Release"

B = "${WORKDIR}/build"
OUT_DIR = "${B}/${BUILD_TYPE}"

WEBRUNTIME_BUILD_TARGET = "webos:weboswebruntime"
BROWSER_APP_BUILD_TARGET = "chrome"
BROWSER_APPLICATION = "chromium84-browser"
BROWSER_APPLICATION_DIR = "/opt/chromium84"

TARGET = "${WEBRUNTIME_BUILD_TARGET} ${BROWSER_APP_BUILD_TARGET}"

# Skip do_install:append of webos_system_bus. It is not compatible with this component.
WEBOS_SYSTEM_BUS_FILES_LOCATION = "${S}/files/sysbus"

PACKAGECONFIG ?= "jumbo use-upstream-wayland"


# Set a default value for jumbo file merge of 8. This should be good for build
# servers and workstations with a big number of cores. In case build is
# happening in a machine with less cores but still enough RAM a good value could
# be 50.
JUMBO_FILE_MERGE_LIMIT="8"
PACKAGECONFIG[jumbo] = "use_jumbo_build=true jumbo_file_merge_limit=${JUMBO_FILE_MERGE_LIMIT}, use_jumbo_build=false"

PACKAGECONFIG[lttng] = "use_lttng=true,use_lttng=false,lttng-ust,lttng-tools lttng-modules babeltrace"

# Chromium can use v4l2 device for hardware accelerated video decoding on such boards as Renesas R-car M3, for example.
# In case of R-car m3, additional patches are required for gstreamer and v4l2apps.
# See https://github.com/igel-oss/meta-browser-hwdecode/tree/igalia-chromium71.
PACKAGECONFIG[use-linux-v4l2] = "use_v4l2_codec=true use_v4lplugin=true use_linux_v4l2_only=true"

PACKAGECONFIG[use-upstream-wayland] = " \
    ozone_platform_wayland_external=false ozone_platform_wayland=true, \
    ozone_platform_wayland_external=true ozone_platform_wayland=false \
"

GN_ARGS = "\
    enable_memorymanager_webapi=false\
    ffmpeg_branding=\"Chrome\"\
    host_os=\"linux\"\
    ozone_auto_platforms=false\
    proprietary_codecs=true\
    target_os=\"linux\"\
    treat_warnings_as_errors=false\
    is_agl=true\
    use_cbe=true\
    is_chrome_cbe=true\
    is_component_build=false\
    use_cups=false\
    use_custom_libcxx=false\
    use_kerberos=false\
    use_neva_media=false\
    use_ozone=true\
    use_xkbcommon=true\
    use_wayland_gbm=false\
    use_pmlog=false\
    use_system_debugger_abort=true\
    ${PACKAGECONFIG_CONFARGS}\
"

# From Chromium's BUILDCONFIG.gn:
# Set to enable the official build level of optimization. This has nothing
# to do with branding, but enables an additional level of optimization above
# release (!is_debug). This might be better expressed as a tri-state
# (debug, release, official) but for historical reasons there are two
# separate flags.
# See also: https://groups.google.com/a/chromium.org/d/msg/chromium-dev/hkcb6AOX5gE/PPT1ukWoBwAJ
GN_ARGS += "is_debug=false is_official_build=true"

# is_cfi default value is true for x86-64 builds with is_official_build=true.
# As of M63, we explicitly need to set it to false, otherwise we fail the
# following assertion in //build/config/sanitizers/sanitizers.gni:
#   assert(!is_cfi || is_clang,
#          "is_cfi requires setting is_clang = true in 'gn args'")
GN_ARGS += "is_cfi=false"

# By default, passing is_official_build=true to GN causes its symbol_level
# variable to be set to "2". This means the compiler will be passed "-g2" and
# we will end up with a very large chrome binary (around 5Gb as of M58)
# regardless of whether DEBUG_BUILD has been set or not. In addition, binutils,
# file and other utilities are unable to read a 32-bit binary this size, which
# causes it not to be stripped.
# The solution is two-fold:
# 1. Make sure -g is not passed on 32-bit architectures via DEBUG_FLAGS. -g is
#    the same as -g2. -g1 generates an 800MB binary, which is a lot more
#    manageable.
# 2. Explicitly pass symbol_level=0 to GN. This causes -g0 to be passed
#    instead, so that if DEBUG_BUILD is not set GN will not create a huge debug
#    binary anyway. Since our compiler flags are passed after GN's, -g0 does
#    not cause any issues if DEBUG_BUILD is set, as -g1 will be passed later.
DEBUG_FLAGS:remove:arm = "-g"
DEBUG_FLAGS:append:arm = "-g1"
DEBUG_FLAGS:remove:x86 = "-g"
DEBUG_FLAGS:append:x86 = "-g1"
GN_ARGS += "symbol_level=0"

# We do not want to use Chromium's own Debian-based sysroots, it is easier to
# just let Chromium's build system assume we are not using a sysroot at all and
# let Yocto handle everything.
GN_ARGS += "use_sysroot=false"

# ARM builds need special additional flags (see ${S}/build/config/arm.gni).
# If we do not pass |arm_arch| and friends to GN, it will deduce a value that
# will then conflict with TUNE_CCARGS and CC.
# Note that as of M61 in some corner cases parts of the build system disable
# the "compiler_arm_fpu" GN config, whereas -mfpu is always passed via ${CC}.
# We might want to rework that if there are issues in the future.
def get_compiler_flag(params, param_name, d):
    """Given a sequence of compiler arguments in |params|, returns the value of
    an option |param_name| or an empty string if the option is not present."""
    for param in params:
      if param.startswith(param_name):
        return param.split('=')[1]
    return ''

ARM_FLOAT_ABI = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'hard', 'softfp', d)}"
ARM_FPU = "${@get_compiler_flag(d.getVar('TUNE_CCARGS').split(), '-mfpu', d)}"
ARM_TUNE = "${@get_compiler_flag(d.getVar('TUNE_CCARGS').split(), '-mcpu', d)}"
ARM_VERSION:aarch64 = "8"
ARM_VERSION:armv7a = "7"
ARM_VERSION:armv7ve = "7"
ARM_VERSION:armv6 = "6"

# GN computes and defaults to it automatically where needed
# forcing it from cmdline breaks build on places where it ends up
# overriding what GN wants
TUNE_CCARGS:remove = "-mthumb"

GN_ARGS:append:arm = " \
        arm_float_abi=\"${ARM_FLOAT_ABI}\" \
        arm_fpu=\"${ARM_FPU}\" \
        arm_tune=\"${ARM_TUNE}\" \
        arm_version=${ARM_VERSION} \
"
# tcmalloc's atomicops-internals-arm-v6plus.h uses the "dmb" instruction that
# is not available on (some?) ARMv6 models, which causes the build to fail.
GN_ARGS:append:armv6 += 'use_allocator="none"'
# The WebRTC code fails to build on ARMv6 when NEON is enabled.
# https://bugs.chromium.org/p/webrtc/issues/detail?id=6574
GN_ARGS:append:armv6 += 'arm_use_neon=false'

# Disable glibc shims on musl
# tcmalloc does not play well with musl as of M62 (and possibly earlier).
# https://github.com/gperftools/gperftools/issues/693
GN_ARGS:append:libc-musl = ' use_allocator_shim=false'

# More options to speed up the build
GN_ARGS += "\
    enable_nacl=false\
    disable_ftp_support=true\
    enable_print_preview=false\
    enable_remoting=false\
    use_glib=true\
    use_gnome_keyring=false\
    use_pulseaudio=false\
"

# Doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv6 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"
COMPATIBLE_MACHINE:x86 = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"

#CHROMIUM_PLUGINS_PATH = "${libdir}"
CBE_DATA_PATH = "${libdir}/cbe"
CBE_DATA_LOCALES_PATH = "${CBE_DATA_PATH}/locales"

# The text relocations are intentional -- see comments in [GF-52468]
# TODO: check if we need INSANE_SKIP on ldflags
INSANE_SKIP:${PN} = "textrel ldflags"


do_compile[progress] = "outof:^\[(\d+)/(\d+)\]\s+"
do_compile() {
    if [ ! -f ${OUT_DIR}/build.ninja ]; then
         do_configure
    fi

    export PATH="${S}/depot_tools:$PATH"
    ${S}/depot_tools/ninja ${PARALLEL_MAKE} -v -C ${OUT_DIR} ${TARGET}
}

do_configure() {
    configure_env
}

configure_env() {
    export GYP_CHROMIUM_NO_ACTION=1
    export PATH="${S}/depot_tools:$PATH"

    GN_ARGS="${GN_ARGS}"
    echo GN_ARGS is ${GN_ARGS}
    echo BUILD_TARGETS are ${TARGET}
    cd ${SRC_DIR}
    gn gen ${OUT_DIR} --args="${GN_ARGS}"
}

WINDOW_SIZE ?= "1920,1080"

configure_browser_settings() {
    USER_AGENT="Mozilla/5.0 (Linux; NetCast; U) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/${CHROMIUM_VERSION} Safari/537.31"
    echo "${USER_AGENT}" > ${D_DIR}/user_agent_conf
    #We can replace below WINDOW_SIZE values from build configuration if available
    #echo "${WINDOW_SIZE}" > ${D_DIR}/window_size_conf
}

install_chromium_browser() {
    # Install browser files
    ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} webos/install/default_browser

    D_DIR=${D}${BROWSER_APPLICATION_DIR}
    L_DIR=${D}${CBE_DATA_PATH}
    install -d ${D_DIR}
    install -d ${L_DIR}

    cp -R --no-dereference --preserve=mode,links -v ${OUT_DIR}/image/${BROWSER_APPLICATION_DIR}/* ${D_DIR}

    # AGL does not have PMLOG
    sed -i.bak s/PmLogCtl.*// ${D_DIR}/run_webbrowser

    # To execute chromium in JAILER, Security Part needs permissions change
    # run_webbrowser: Script file for launching chromium
    chmod -v 755 ${D_DIR}/chrome
    chmod -v 755 ${D_DIR}/kill_webbrowser
    chmod -v 755 ${D_DIR}/run_webbrowser

    configure_browser_settings
}

install_webruntime() {

    # # Generate install webos webview files
    # ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} webos/install/weboswebruntime

    # # Move installation files to D directory
    # mv ${OUT_DIR}/image/* ${D}
    # # Rename include and locale directories
    # mv ${D}${includedir}/webruntime ${D}${includedir}/${BPN}
    # mv ${D}${CBE_DATA_PATH}/neva_locales ${D}${CBE_DATA_LOCALES_PATH}

    # # move this to separate mksnapshot-cross recipe once we figure out how to build just cross mksnapshot from chromium repository
    # install -d ${D}${bindir_cross}
    # gzip -c ${OUT_DIR}/${MKSNAPSHOT_PATH}mksnapshot > ${D}${bindir_cross}/${HOST_SYS}-mksnapshot.gz

    install -d ${D}${libdir}
    install -d ${D}${libdir}/swiftshader
    install -d ${D}${includedir}/${BPN}
    install -d ${D}${CBE_DATA_PATH}
    install -d ${D}${CBE_DATA_LOCALES_PATH}

    # Install webos webview files
    ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} webos/install/weboswebruntime

    install -v -m 0644 ${OUT_DIR}/swiftshader/*.so ${D}${libdir}/swiftshader

    cp -R --no-dereference --preserve=mode,links -v ${OUT_DIR}/image/${includedir}/${BPN}/* ${D}${includedir}/${BPN}/
    install -v -m 0644 ${OUT_DIR}/image/${libdir}/*.so ${D}${libdir}

    install -v -m 0644 ${OUT_DIR}/image/${CBE_DATA_PATH}/icudtl.dat ${D}${CBE_DATA_PATH}
    install -v -m 0644 ${OUT_DIR}/image/${CBE_DATA_PATH}/snapshot_blob.bin ${D}${CBE_DATA_PATH}
    # app_runtime_content.pak, webos_content.pak, extensions_shell_and_test.pak
    install -v -m 0644 ${OUT_DIR}/image/${CBE_DATA_PATH}/*.pak ${D}${CBE_DATA_PATH}

    cp -R --no-dereference --preserve=mode,links -v ${OUT_DIR}/image/${CBE_DATA_LOCALES_PATH}/*.pak ${D}${CBE_DATA_LOCALES_PATH}

    chown root:root -R ${D}${libdir}
    chown root:root -R ${D}${includedir}
    chown root:root -R ${D}${CBE_DATA_LOCALES_PATH}

    # move this to separate mksnapshot-cross recipe once we figure out how to build just cross mksnapshot from chromium repository
    install -d ${D}${base_bindir}
    gzip -c ${OUT_DIR}/${MKSNAPSHOT_PATH}mksnapshot > ${D}${base_bindir}/${HOST_SYS}-mksnapshot.gz

}

do_install() {
    install_webruntime
    install_chromium_browser
}

WEBOS_SYSTEM_BUS_DIRS_LEGACY_BROWSER_APPLICATION = " \
    ${webos_sysbus_prvservicesdir}/${BROWSER_APPLICATION}.service \
    ${webos_sysbus_pubservicesdir}/${BROWSER_APPLICATION}.service \
    ${webos_sysbus_prvrolesdir}/${BROWSER_APPLICATION}.json \
    ${webos_sysbus_pubrolesdir}/${BROWSER_APPLICATION}.json \
"

SYSROOT_DIRS:append = " ${bindir_cross}"

PACKAGES:prepend = " \
    ${PN}-cross-mksnapshot \
    ${BROWSER_APPLICATION} \
"

FILES:${BROWSER_APPLICATION} += " \
    ${BROWSER_APPLICATION_DIR} \
    ${WEBOS_SYSTEM_BUS_DIRS_LEGACY_BROWSER_APPLICATION} \
"

RDEPENDS:${BROWSER_APPLICATION} += "${PN}"

VIRTUAL-RUNTIME_gpu-libs ?= ""
RDEPENDS:${PN} += "${VIRTUAL-RUNTIME_gpu-libs}"

# The text relocations are intentional -- see comments in [GF-52468]
# TODO: check if we need INSANE_SKIP on ldflags
INSANE_SKIP:${BROWSER_APPLICATION} += "libdir ldflags textrel"

FILES:${PN} = " \
    ${libdir}/*.so \
    ${libdir}/swiftshader/*.so \
    ${CBE_DATA_PATH}/* \
    ${libdir}/${BPN}/*.so \
    ${WEBOS_SYSTEM_BUS_DIRS} \
"

FILES:${PN}-dev = " \
    ${includedir} \
"

FILES:${PN}-cross-mksnapshot = "${bindir_cross}/${HOST_SYS}-mksnapshot.gz"
