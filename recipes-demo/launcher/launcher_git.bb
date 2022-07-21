SUMMARY     = "AGL Launcher Application"
DESCRIPTION = "AGL Launcher Application build with recipe method"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/apps/launcher"
SECTION     = "apps"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "\
        qtbase \
        qtdeclarative \
        qtquickcontrols2 \
        qtwebsockets \
        wayland-native \
        wayland \
        qtwayland \
        qtwayland-native \
        agl-compositor \
        json-c \
        applaunchd \
"

PV      = "1.0+git${SRCPV}"

SRC_URI = " \
        git://gerrit.automotivelinux.org/gerrit/apps/launcher;protocol=https;branch=${AGL_BRANCH} \
        file://launcher.service \
        "
SRCREV  = "21a1fef41137a1dffc6ff0a602665999a7f6cc66"

S       = "${WORKDIR}/git"

inherit qmake5 systemd pkgconfig

PATH:prepend = "${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}:"

do_install:append() {
    install -d ${D}${systemd_user_unitdir}/agl-session.target.wants
    install -m0644 ${WORKDIR}/launcher.service ${D}${systemd_user_unitdir}/launcher.service
    ln -s ../launcher.service ${D}${systemd_user_unitdir}/agl-session.target.wants/launcher.service
}

FILES:${PN} += " ${systemd_user_unitdir}"

RDEPENDS:${PN} += " \
    applaunchd \
    homescreen \
"
