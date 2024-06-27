SRC_URI:append = " file://0001-agl-cef-Snapshot-with-all-patches-needed-to-enable-w.patch"

do_configure[depends] += "cef:do_populate_sysroot"

CEF_ROOT = "${STAGING_DATADIR}/cef"
WAM_OUT_DIR = "${datadir}/wam"

RUNTIME = "llvm"
TOOLCHAIN = "clang"

DEPENDS:append:toolchain-clang = " clang-cross-${TARGET_ARCH}"

PREFERRED_PROVIDER_libgcc = "compiler-rt"

COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv7 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"
COMPATIBLE_MACHINE:x86 = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"

EXTRA_OECMAKE = "\
    -DPROJECT_ARCH=${TUNE_ARCH} \
    -DCMAKE_BUILD_TYPE=Release \
    -DCMAKE_INSTALL_PREFIX=${prefix} \
    -DPLATFORM_NAME=${@'${DISTRO}'.upper().replace('-', '_')} \
    -DCEF_ROOT=${CEF_ROOT} \
    -DCMAKE_INSTALL_BINDIR=${WAM_OUT_DIR}"

do_install:append() {
    cp -R --no-dereference --preserve=mode,links -v ${CEF_ROOT}/Release/* ${D}${WAM_OUT_DIR}
    cp -R --no-dereference --preserve=mode,links -v ${CEF_ROOT}/Resources/* ${D}${WAM_OUT_DIR}
    install -v -D -m 644 ${WORKDIR}/WebAppMgr-cef.env ${D}${sysconfdir}/default/WebAppMgr.env
    sed -i -e 's#@WAM_EXE_DIR@#${WAM_OUT_DIR}#g' ${D}${systemd_system_unitdir}/WebAppMgr.service
}

INSANE_SKIP:${PN} += "already-stripped file-rdeps libdir"

FILES:${PN} += "${WAM_OUT_DIR}"
