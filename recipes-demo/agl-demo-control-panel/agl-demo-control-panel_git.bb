SUMMARY     = "AGL demo control panel"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=685e0faaaec2c2334cf8159ca6bd2975"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/agl-demo-control-panel;protocol=https;branch=${AGL_BRANCH} \
           file://agl-demo-control-panel.service \
"
SRCREV = "bd61c899728016a850472387be468b8058a1309f"

S = "${WORKDIR}/git"

inherit systemd allarch

require recipes-config/agl-users/agl-users.inc

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    # There's no provision for a Pythonic install into /usr/lib, so dump
    # into a directory /usr/libexec.
    install -d ${D}${libexecdir}/${BPN}
    cp -drv ${S}/* ${D}${libexecdir}/${BPN}

    # Remove stray shell script from Docker container build support to
    # avoid QA complaints
    rm -rf ${D}${libexecdir}/${BPN}/docker

    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service
    
    # Install conf file
    install -d ${D}/home/agl-driver/.local/share/agl-demo-control-panel
    sed 's/=user-session/=AGL-databroker/' ${S}/extras/config.ini > \
        ${D}/home/agl-driver/.local/share/agl-demo-control-panel/config.ini
    chown -R agl-driver:agl-driver ${D}/home/agl-driver
}

# For now generate resource wrapper on first boot, as it looks non-trivial
# to get python3-pyqt5-native working to run pyrcc5 during build.
pkg_postinst_ontarget:${PN} () {
    /usr/bin/pyrcc5 -o ${libexecdir}/${BPN}/res_rc.py ${libexecdir}/${BPN}/assets/res.qrc
    true
}

FILES:${PN} += "/home/agl-driver"

RDEPENDS:${PN} += " \
    python3 \
    python3-modules \
    python3-packaging \
    python3-qtwidgets \
    python3-can \
    agl-users \
    weston \
"
