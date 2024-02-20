SUMMARY     = "Navigation application."
DESCRIPTION = "AGL demonstration Navigation application based on QtLocation widget."
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/ondemandnavi"
SECTION     = "apps"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "qtquickcontrols2 qtlocation libqtappfw"

PV = "2.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/ondemandnavi;protocol=https;branch=${AGL_BRANCH} \
           file://navigation.conf \
           file://navigation.conf.kvm-demo \
           file://navigation.token \
"
SRCREV = "d6b883ef6bdb63fb501b03d64c9a32bc29b7b2c7"

S = "${WORKDIR}/git"

inherit qmake5 pkgconfig agl-app update-alternatives

AGL_APP_ID = "navigation"
AGL_APP_NAME = "Navigation"

do_install:append() {
    # Currently using default global client and CA certificates
    # for KUKSA.val SSL, installing app specific ones would go here.

    # VIS authorization token file for KUKSA.val should ideally not
    # be readable by other users, but currently that's not doable
    # until a packaging/sandboxing/MAC scheme is (re)implemented or
    # something like OAuth is plumbed in as an alternative.
    install -d ${D}${sysconfdir}/xdg/AGL/navigation
    install -m 0644 ${WORKDIR}/navigation.conf ${D}${sysconfdir}/xdg/AGL/navigation.conf.default
    install -m 0644 ${WORKDIR}/navigation.conf.kvm-demo ${D}${sysconfdir}/xdg/AGL/
    install -m 0644 ${WORKDIR}/navigation.token ${D}${sysconfdir}/xdg/AGL/navigation/
}

ALTERNATIVE_LINK_NAME[navigation.conf] = "${sysconfdir}/xdg/AGL/navigation.conf"

PACKAGE_BEFORE_PN += "${PN}-conf ${PN}-conf-kvm-demo"

FILES:${PN}-conf += "${sysconfdir}/xdg/AGL/navigation.conf.default"
RDEPENDS:${PN}-conf = "${PN}"
RPROVIDES:${PN}-conf = "navigation.conf"
RCONFLICTS:${PN}-conf = "${PN}-conf-kvm-demo"
ALTERNATIVE:${PN}-conf = "navigation.conf"
ALTERNATIVE_TARGET_${PN}-conf = "${sysconfdir}/xdg/AGL/navigation.conf.default"

FILES:${PN}-conf-kvm-demo += "${sysconfdir}/xdg/AGL/navigation.conf.kvm-demo"
RDEPENDS:${PN}-conf-kvm-demo = "${PN}"
RPROVIDES:${PN}-conf-kvm-demo = "navigation.conf"
RCONFLICTS:${PN}-conf-kvm-demo = "${PN}-conf"
ALTERNATIVE:${PN}-conf-kvm-demo = "navigation.conf"
ALTERNATIVE_TARGET_${PN}-conf-kvm-demo = "${sysconfdir}/xdg/AGL/navigation.conf.kvm-demo"

RDEPENDS:${PN} += " \
    qtwayland \
    qtbase-qmlplugins \
    qtgraphicaleffects-qmlplugins \
    qtquickcontrols-qmlplugins \
    qtquickcontrols2-qmlplugins \
    qtquickcontrols2-agl \
    qtquickcontrols2-agl-style \
    qtlocation \
    flite \
    libqtappfw \
    openjtalk \
    gstreamer1.0 \
    ondemandnavi-config \
"
