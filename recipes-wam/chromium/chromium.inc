# Copyright (c) 2018 LG Electronics, Inc.

SUMMARY = "Chromium webruntime for webOS"
AUTHOR = "Lokesh Kumar Goel <lokeshkumar.goel@lge.com>"
SECTION = "webos/apps"
LICENSE = "Apache-2.0 & BSD-3-Clause & LGPL-2.0-only & LGPL-2.1-only"
LIC_FILES_CHKSUM = "\
    file://src/LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d \
    file://src/third_party/blink/renderer/core/LICENSE-LGPL-2;md5=36357ffde2b64ae177b2494445b79d21 \
    file://src/third_party/blink/renderer/core/LICENSE-LGPL-2.1;md5=a778a33ef338abbaf8b8a7c36b6eec80 \
"

require gn-utils.inc

inherit gettext qemu python3native pkgconfig

DEPENDS = "virtual/gettext wayland wayland-native pixman freetype glib-2.0 fontconfig openssl pango cairo icu libxkbcommon libexif dbus pciutils udev libcap alsa-lib virtual/egl elfutils-native libdrm atk at-spi2-atk gperf-native gconf nss nss-native nspr nspr-native bison-native qemu-native curl"

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

TARGET = "${WEBRUNTIME_BUILD_TARGET}"

# Skip do_install:append of webos_system_bus. It is not compatible with this component.
WEBOS_SYSTEM_BUS_FILES_LOCATION = "${S}/files/sysbus"

PACKAGECONFIG[lttng] = "use_lttng=true,use_lttng=false,lttng-ust,lttng-tools lttng-modules babeltrace"

# Chromium can use v4l2 device for hardware accelerated video decoding on such boards as Renesas R-car M3, for example.
# In case of R-car m3, additional patches are required for gstreamer and v4l2apps.
# See https://github.com/igel-oss/meta-browser-hwdecode/tree/igalia-chromium71.
PACKAGECONFIG[use-linux-v4l2] = "use_v4l2_codec=true use_v4lplugin=true use_linux_v4l2_only=true"

# Options to enable debug build. Add this PACKAGECONFIG to local.conf
# to enable debug build. By default debug is completely disabled to
# speed up build
PACKAGECONFIG[debug] = "symbol_level=2 use_debug_fission=true,symbol_level=1"
PACKAGECONFIG[debug-blink] = "blink_symbol_level=2 use_debug_fission=true,blink_symbol_level=1"

GN_ARGS = "\
    enable_memorymanager_webapi=false\
    ffmpeg_branding=\"Chrome\"\
    host_os=\"linux\"\
    ozone_auto_platforms=false\
    proprietary_codecs=true\
    target_os=\"linux\"\
    treat_warnings_as_errors=false\
    use_x11=false\
    is_agl=true\
    use_cbe=true\
    is_chrome_cbe=false\
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

# use_thin_lto should be also disabled for GCC
GN_ARGS += "use_thin_lto=false"

# We do not want to use Chromium's own Debian-based sysroots, it is easier to
# just let Chromium's build system assume we are not using a sysroot at all and
# let Yocto handle everything.
GN_ARGS += "use_sysroot=false"

# Reduce the size of the build by removing swiftshader and dawn.
# This will effectively block support for WebGPU.
# TODO(jdapena): reenable WebGPU support
GN_ARGS += "use_swiftshader=false enable_swiftshader_vulkan=false use_dawn=false"

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
GN_ARGS:append:armv6 = ' use_allocator="none"'
# The WebRTC code fails to build on ARMv6 when NEON is enabled.
# https://bugs.chromium.org/p/webrtc/issues/detail?id=6574
GN_ARGS:append:armv6 = ' arm_use_neon=false'

# Disable glibc shims on musl
# tcmalloc does not play well with musl as of M62 (and possibly earlier).
# https://github.com/gperftools/gperftools/issues/693
GN_ARGS:append:libc-musl = ' use_allocator_shim=false'

# Chromium 91 build of highway with NEON is broken. Do not build.
GN_ARGS:append:aarch64 = ' enable_jxl_decoder=false'

# More options to speed up the build
GN_ARGS += "\
    enable_nacl=false\
    disable_ftp_support=true\
    enable_print_preview=false\
    enable_basic_printing=false\
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
    ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} ${TARGET}
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

install_webruntime() {

    # # Generate install webos webview files
    # ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} webos/install/weboswebruntime

    # # Move installation files to D directory
    # mv ${OUT_DIR}/image/* ${D}
    # # Rename include and locale directories
    # mv ${D}${includedir}/webruntime ${D}${includedir}/${BPN}
    # mv ${D}${CBE_DATA_PATH}/neva_locales ${D}${CBE_DATA_LOCALES_PATH}

    install -d ${D}${libdir}
    # install -d ${D}${libdir}/swiftshader
    install -d ${D}${includedir}/${BPN}
    install -d ${D}${CBE_DATA_PATH}
    install -d ${D}${CBE_DATA_LOCALES_PATH}

    # Install webos webview files
    ${S}/depot_tools/ninja ${PARALLEL_MAKE} -C ${OUT_DIR} webos/install/weboswebruntime

    # install -v -m 0644 ${OUT_DIR}/swiftshader/*.so ${D}${libdir}/swiftshader

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
}

do_install() {
    install_webruntime
}

SYSROOT_DIRS:append = " ${bindir_cross}"

VIRTUAL-RUNTIME_gpu-libs ?= ""
RDEPENDS:${PN} += "${VIRTUAL-RUNTIME_gpu-libs}"

FILES:${PN} = " \
    ${libdir}/*.so \
    ${CBE_DATA_PATH}/* \
    ${libdir}/${BPN}/*.so \
"

FILES:${PN}-dev = " \
    ${includedir} \
"
