SUMMARY     = "Instrument Cluster receiver application"
DESCRIPTION = "AGL demonstration instrument cluster Qt remote display application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/admin/repos/apps/agl-qt-cluster-demo-receiver"
SECTION     = "apps"

LICENSE     = "Apache-2.0 & MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984 \
                    file://app/surface.hpp;beginline=5;endline=21;md5=5351c531a191f0e3463aafcd0a6a00a3"

DEPENDS = " \
    wayland wayland-native \
    qtwayland qtwayland-native \
    qtquickcontrols2 qtwebsockets qtbase qtdeclarative \
    gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-qt-cluster-demo-receiver;protocol=https;branch=${AGL_BRANCH} \
           file://qt-cluster-receiver.service \
"
SRCREV  = "867f8df04291c9b9e7d5087da22ed7782f98a1ea"

S  = "${WORKDIR}/git"

inherit cmake_qt5 pkgconfig

do_install:append() {
    # Only install unit, do not enable it by default
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_user_unitdir}/${BPN}.service
}

FILES:${PN} += " ${systemd_user_unitdir}"

RDEPENDS:${PN} += " \
    qtwayland \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
"
