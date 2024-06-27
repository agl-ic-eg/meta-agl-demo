require gn-utils.inc

inherit qemu python3native

LICENSE = "Apache-2.0 & BSD-3-Clause & LGPL-2.0-only & LGPL-2.1-only"

LIC_FILES_CHKSUM = "\
    file://LICENSE;md5=c408a301e3407c3803499ce9290515d6 \
    file://third_party/blink/renderer/core/LICENSE-LGPL-2;md5=36357ffde2b64ae177b2494445b79d21 \
    file://third_party/blink/renderer/core/LICENSE-LGPL-2.1;md5=a778a33ef338abbaf8b8a7c36b6eec80 \
"

CHROMIUM_VERSION = "118.0.5993.80"
BRANCH = "5993"
SRCREV = "3cffa575446727e2fe1f6499efa21f8e096e8ca0"

PV = "${CHROMIUM_VERSION}.${BRANCH}+git"

FILESEXTRAPATHS:prepend := "${THISDIR}/files/cef:"
FILESEXTRAPATHS:prepend := "${THISDIR}/files/chromium:"

# The [agl]-prefixed patches are the changes needed to make WAM work with
# agl-compositor. The [meta-browser] patches comes from the https://github.com/OSSystems/meta-browser
# project that does similar work on keeping up to the new chromium milestones
# and the [MXX-Fix] patches are the fixes done by us to fix build issues for
# the current chromium milestone. 
# For more information about the current milestones: https://chromiumdash.appspot.com/releases?platform=Linux
SRC_URI = "\
    https://commondatastorage.googleapis.com/chromium-browser-official/chromium-${CHROMIUM_VERSION}.tar.xz \
    file://0001-agl-compositor-Add-agl_shell_wrapper-AGL-wayland-ext.patch \
    file://0002-agl-Add-waylandwindow-window-tree-host-essential-par.patch \
    file://0003-agl-Only-bind-to-agl_shell-if-it-s-the-browser-proce.patch \
    file://0004-agl-Add-a-method-to-check-if-the-agl-window-is-confi.patch \
    file://0005-agl-Start-using-agl-shell-version-4.patch \
    file://0006-agl-Don-t-use-DRI-for-renesas.patch \
    file://0007-meta-browser-Remove-the-GN-settings-done-for-clang-t.patch \
    file://0008-meta-browser-Pass-no-static-libstdc-to-gen.py.patch \
    file://0009-meta-browser-IWYU-Add-includes-for-size_t-and-int64_.patch \
    file://0010-meta-browser-BUILD-do-not-specify-march-on-arm.patch \
    file://0011-meta-browser-Avoid-parenthesized-initialization-of-a.patch \
    file://0012-meta-browser-Fix-constexpr-variable-cannot-have-non-.patch \
    file://0013-meta-browser-Add-missing-typename-s.patch \
    file://0014-meta-browser-Avoid-std-ranges-find_if.patch \
    file://0015-meta-browser-Avoid-capturing-structured-bindings.patch \
    file://0016-meta-browser-Delete-compiler-options-not-available-i.patch \
    file://0017-meta-browser-Don-t-pass-disable-auto-upgrade-debug-i.patch \
    file://0018-meta-browser-Fix-undefined-symbol-PaintOpWriter-Seri.patch \
    file://0019-upstream-Initialize-ServerCvc-with-designated-initia.patch \
    file://0020-M118-fix-Don-t-look-for-depot_tools-in-chrommium-s-t.patch \
    file://0021-M118-fix-Add-multiple-missing-includes.patch \
    file://0022-M118-fix-Fix-aggregate-initialization-in-trace_log.patch \
    file://0023-M118-fix-Add-missing-typename-keyword-in-multiple-st.patch \
    file://0024-M118-fix-Fix-comparison-in-HostResolverCache.patch \
    file://0025-M118-fix-Avoid-using-std-ranges-any_of-find_if-none_.patch \
    file://0026-M118-fix-Add-deleted-constructors-operators.patch \
    file://0027-M118-fix-Initialize-percentages-member-on-blink-Font.patch \
    file://0028-M118-fix-Don-t-delete-ZstdSourceStream-copy-move-cto.patch \
    file://0029-M118-fix-Fix-issue-with-structured-bindinds-captured.patch \
    file://0030-M118-fix-Only-default-arm_use_neon-to-true-if-its-va.patch \
    file://0031-M118-fix-Add-a-way-to-set-different-lib-paths-host-a.patch \
    file://0032-M118-fix-zlib-Fix-arm-build.patch \
    file://0033-M118-fix-Fix-skia-linker-issues-for-arm-neon.patch \
    file://0034-v8-qemu-wrapper.patch \
    \
    git://bitbucket.org/chromiumembedded/cef.git;branch=${BRANCH};protocol=https;rev=${SRCREV};name=cef;destsuffix=chromium-${CHROMIUM_VERSION}/cef \
    file://0001-Add-an-option-to-use-an-output-directory-outside-src.patch;patchdir=cef \
    file://0002-Add-an-option-to-override-the-default-distrib-direct.patch;patchdir=cef \
    file://0003-Add-an-option-to-use-an-alternative-base-output-dire.patch;patchdir=cef \
    file://0004-Add-an-option-to-bypass-sysroot-checking-and-force.patch;patchdir=cef \
    file://0005-Add-AGL-wayland-window-related-calls.patch;patchdir=cef \
    file://0006-Add-a-method-to-check-if-the-agl-window-is-configure.patch;patchdir=cef \
    file://0007-Add-the-SetActivateRegion-method.patch;patchdir=cef \
    file://0008-Allow-passing-the-app_id-on-widget-creation.patch;patchdir=cef \
    file://0009-Update-generated-api.patch;patchdir=cef \
    file://0010-Make-patcher-work-outside-a-git-checkout.patch;patchdir=cef \ 
    file://0011-Avoid-the-RuntimeError-dictionary-changed-size-durin.patch;patchdir=cef \
"

SRC_URI[sha256sum] = "741c5528a151bc364999969077a13d7a283cfd0eaf34adf47de667a34e5e58ff"

CHROMIUM_DIR = "${WORKDIR}/chromium-${CHROMIUM_VERSION}"
CEF_DIR = "${CHROMIUM_DIR}/cef"
DEPOT_TOOLS_DIR="${STAGING_DIR_NATIVE}${datadir}/depot_tools"
S = "${CHROMIUM_DIR}"
B = "${WORKDIR}/build"

OUT_PATH = "${B}/out/Release_GN_${GN_TARGET_ARCH_NAME}"
DIST_PATH = "${OUT_PATH}/dist/cef-minimal_${GN_TARGET_ARCH_NAME}"
CEF_DATA_PATH = "${datadir}/cef"

DEPENDS:append = " ca-certificates-native curl clang clang-native gperf-native dbus libcxx libcxx-native libpng libxslt jpeg jpeg-native compiler-rt libxkbcommon nss nss-native atk at-spi2-atk libdrm pango cairo virtual/egl qemu-native pciutils glib-2.0 pkgconfig-native pulseaudio xz-native compiler-rt compiler-rt-native expat-native"

do_sync[depends] += "depot-tools-wam-native:do_populate_sysroot"
do_configure[depends] += "depot-tools-wam-native:do_populate_sysroot"
do_compile[depends] += "depot-tools-wam-native:do_populate_sysroot"

# needs to fetch a font package
do_configure[network] = "1"

GN_UNBUNDLE_LIBS = " libjpeg libpng libxslt"

# gn defaults from CEF wiki, except for use_sysroot
GN_DEFINES = "use_sysroot=false \
              symbol_level=0 \
              is_cfi=false \
              use_thin_lto=false \
"

# Disable GTK and prevent cef from
# building its gtk demos
GN_DEFINES:append = " \
              use_gtk=false \
              cef_use_gtk=false \
"

GN_DEFINES:append = " \
              treat_warnings_as_errors=false \
              is_component_build=false \
              use_cups=false \
              use_kerberos=false \
              use_ozone=true \
              use_xkbcommon=true \
              use_wayland_gbm=true \
              use_gnome_keyring=false \
              enable_remoting=false \
              enable_js_type_check=false \
"

# ozone options
GN_DEFINES:append = " \
              use_ozone=true \
              ozone_auto_platforms=false \
              ozone_platform_headless=true \
              ozone_platform_wayland=true \
              ozone_platform_x11=false \
              use_system_minigbm=true \
              use_system_libdrm=true \
              use_system_libwayland=false \
              use_system_libffi=true \
"

GN_DEFINES:append = " \
              dcheck_always_on=false \
              is_debug=false \
              is_official_build=true \
"

GN_DEFINES:append = " \
              use_egl=true \
              use_glib=true \
              use_dri=false \
"

RUNTIME = "llvm"
TOOLCHAIN = "clang"
TOOLCHAIN:class-native = "clang"
LIBCPLUSPLUS = "-stdlib=libc++"

BUILD_CPPFLAGS:append:runtime-llvm = " -isysroot=${STAGING_DIR_NATIVE} -stdlib=libc++"
BUILD_LDFLAGS:append:runtime-llvm = " -rtlib=libgcc -unwindlib=libgcc -stdlib=libc++ -lc++abi -rpath ${STAGING_LIBDIR_NATIVE}"
CXXFLAGS:append:runtime-llvm = " -isysroot=${STAGING_DIR_NATIVE} -stdlib=libc++"

BUILD_AR:toolchain-clang = "llvm-ar"
BUILD_CC:toolchain-clang = "clang"
BUILD_CXX:toolchain-clang = "clang++"
BUILD_LD:toolchain-clang = "clang"

COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv6 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"
COMPATIBLE_MACHINE:x86 = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"

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

GN_DEFINES:append:arm = " \
        arm_float_abi=\"${ARM_FLOAT_ABI}\" \
        arm_fpu=\"${ARM_FPU}\" \
        arm_tune=\"${ARM_TUNE}\" \
        arm_version=${ARM_VERSION} \
"
# tcmalloc's atomicops-internals-arm-v6plus.h uses the "dmb" instruction that
# is not available on (some?) ARMv6 models, which causes the build to fail.
GN_DEFINES:append:armv6 = ' use_allocator="none"'
# The WebRTC code fails to build on ARMv6 when NEON is enabled.
# https://bugs.chromium.org/p/webrtc/issues/detail?id=6574

# Disable unknown attribute warnings that are generating tons of logs
# TODO(rzanoni): check if https://chromium-review.googlesource.com/c/chromium/src/+/4322480
# needs to be reverted
BUILD_CXXFLAGS:remove = '-Wunknown-attributes'
BUILD_CXXFLAGS:append = ' -Wno-unknown-attributes'
BUILD_CPPFLAGS:remove = '-Wunknown-attributes'
BUILD_CPPFLAGS:append = ' -Wno-unknown-attributes'
CXXFLAGS:remove = '-Wunknown-attributes'
CXXFLAGS:append = ' -Wno-unknown-attributes'
CPPFLAGS:remove = '-Wunknown-attributes'
CPPFLAGS:append = ' -Wno-unknown-attributes'

GN_DEFINES:append = ' \
              arm_use_neon=false \
              use_lld=true \
              use_gold=false \
              use_custom_libcxx_for_host=false \
              use_custom_libcxx=false \
              chrome_pgo_phase=0 \
              gold_path="" \
              is_clang=true \
              current_os="linux" \
              clang_use_chrome_plugins=false \
              clang_base_path="${STAGING_DIR_NATIVE}/usr" \
              clang_version="14.0.6" \
              clang_base_path_target="${STAGING_DIR_TARGET}/usr" \
              custom_toolchain="//build/toolchain/cros:target" \
              host_toolchain="//build/toolchain/cros:host" \
              v8_snapshot_toolchain="//build/toolchain/cros:v8_snapshot" \
              target_cpu="${@gn_target_arch_name(d)}" \
              use_v8_context_snapshot=false \
              custom_toolchain="//build/toolchain/yocto:yocto_target" \
              host_toolchain="//build/toolchain/yocto:yocto_native" \
              v8_snapshot_toolchain="//build/toolchain/yocto:yocto_target" \
'

PACKAGECONFIG ??= "upower use-egl"
PACKAGECONFIG[use-egl] = ",,virtual/egl virtual/libgles2"
PACKAGECONFIG[upower] = ",,,upower"

GN_DEFINES:append = ' \
              ${PACKAGECONFIG_CONFARGS} \
'

python do_write_toolchain_file () {
    """Writes a BUILD.gn file for Yocto detailing its toolchains."""
    toolchain_dir = d.expand("${S}/build/toolchain/yocto")
    bb.utils.mkdirhier(toolchain_dir)
    toolchain_file = os.path.join(toolchain_dir, "BUILD.gn")
    write_toolchain_file(d, toolchain_file)
}
addtask write_toolchain_file after do_patch before do_configure

# V8's JIT infrastructure requires binaries such as mksnapshot and
# mkpeephole to be run in the host during the build. However, these
# binaries must have the same bit-width as the target (e.g. a x86_64
# host targeting ARMv6 needs to produce a 32-bit binary). Instead of
# depending on a third Yocto toolchain, we just build those binaries
# for the target and run them on the host with QEMU.
python do_create_v8_qemu_wrapper () {
    """Creates a small wrapper that invokes QEMU to run some target V8 binaries
    on the host."""
    qemu_libdirs = [d.expand('${STAGING_DIR_HOST}${libdir}'),
                    d.expand('${STAGING_DIR_HOST}${base_libdir}')]
    qemu_cmd = qemu_wrapper_cmdline(d, d.getVar('STAGING_DIR_HOST', True),
                                    qemu_libdirs)
    wrapper_path = d.expand('${OUT_PATH}/v8-qemu-wrapper.sh')
    with open(wrapper_path, 'w') as wrapper_file:
        wrapper_file.write("""#!/bin/sh

# This file has been generated automatically.
# It invokes QEMU to run binaries built for the target in the host during the
# build process.

%s "$@"
""" % qemu_cmd)
    os.chmod(wrapper_path, 0o755)
}
do_create_v8_qemu_wrapper[dirs] = "${OUT_PATH}"
addtask create_v8_qemu_wrapper after do_patch before do_configure

do_configure () {
    bbnote "do_configure:"
    bbnote "Base out path: ${B}"

    export DEPOT_TOOLS_UPDATE=0
    export GCLIENT_PY3=1
    export PATH="${DEPOT_TOOLS_DIR}:$PATH"
    export GN_DEFINES="${GN_DEFINES}"
    export SSL_CERT_DIR="$OECORE_NATIVE_SYSROOT/etc/ssl/certs/"
    cd ${S}
    python3 ./build/linux/unbundle/replace_gn_files.py --system-libraries ${GN_UNBUNDLE_LIBS}

    # Download a few dependencies.  Check the current chromium DEPS file when
    # upgrading to a new milestone.
    vpython3 third_party/depot_tools/download_from_google_storage.py --no_resume --extract --no_auth --bucket chromium-fonts -s third_party/test_fonts/test_fonts.tar.gz.sha1
    vpython3 third_party/depot_tools/download_from_google_storage.py --no_resume --extract --no_auth --bucket chromium-nodejs/16.13.0 -s third_party/node/linux/node-linux-x64.tar.gz.sha1
    vpython3 tools/rust/update_rust.py

    cd ${S}/cef
    python3 tools/gclient_hook.py --base-out-path ${B} --bypass-sysroot-check
}

do_compile[progress] = "outof:^\[(\d+)/(\d+)\]\s+"
do_compile () {
    if [ ! -f ${OUT_PATH}/build.ninja ]; then
         do_configure
    fi

    export PATH="${DEPOT_TOOLS_DIR}:$PATH"
    export PATH="$PATH:${S}/third_party/ninja"
    ninja ${PARALLEL_MAKE} -C ${OUT_PATH} libcef chrome_sandbox
}

do_install () {
    cd ${S}/cef
    python3 tools/make_distrib.py --output-dir ${OUT_PATH}/dist \
                                  --dist-path-name cef-minimal \
                                  --base-out-path ${B} \
                                  --no-docs \
                                  --no-symbols \
                                  --no-archive \
                                  --ninja-build \
                                  --minimal \
                                  --${GN_TARGET_ARCH_NAME}-build \
                                  --ozone

    install -d ${D}${CEF_DATA_PATH}

    cp -R --no-dereference --preserve=mode,links -v ${DIST_PATH}/* ${D}${CEF_DATA_PATH}
    # TODO(rzanoni): Follow the wiki instructions to install the sandbox
}

# TODO: fix QA issues, libraries in the wrong location
FILES:${PN} += " \
    ${CEF_DATA_PATH} \
"

INSANE_SKIP:${PN} += "libdir"

PROVIDES:${PN} += "cef"
