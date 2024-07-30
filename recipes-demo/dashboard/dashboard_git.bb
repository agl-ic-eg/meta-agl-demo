SUMMARY     = "Dashboard application"
DESCRIPTION = "AGL demonstration Dashboard application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/dashboard"
SECTION     = "apps"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qttools-native \
    qtdeclarative \
    libqtappfw \
"

PV = "2.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/dashboard;protocol=https;branch=${AGL_BRANCH} \
           file://dashboard.conf \
           file://dashboard.token \
           file://0001-Migrate-to-Qt-6.patch \
           "
SRCREV  = "b1b67ab525555a082ca257ad08fc4fba9b1f74e3"

S = "${WORKDIR}/git"

inherit qt6-qmake pkgconfig agl-app

AGL_APP_NAME = "Dashboard"

do_install:append() {
    # Currently using default global client and CA certificates
    # for KUKSA.val SSL, installing app specific ones would go here.

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/dashboard
    install -m 0644 ${WORKDIR}/dashboard.conf ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/dashboard.token ${D}${sysconfdir}/xdg/AGL/dashboard/
}

RDEPENDS:${PN} += " \
    qtwayland \
    qtbase-qmlplugins \
    qt5compat \
    qtquickcontrols2-agl-style \
"
