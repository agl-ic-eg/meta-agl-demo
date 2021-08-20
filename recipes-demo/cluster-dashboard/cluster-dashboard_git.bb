SUMMARY     = "Instrument Cluster Dashboard application"
DESCRIPTION = "AGL demonstration instrument cluster dashboard application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-cluster-demo-dashboard"
SECTION     = "apps"

LICENSE     = "Apache-2.0 & BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984 \
                    file://app/cluster-gauges.qml;beginline=9;endline=48;md5=54187d50b29429abee6095fe8b7c1a78"

DEPENDS = " \
    qtquickcontrols2 libqtappfw glib-2.0 \
    wayland-native wayland qtwayland qtwayland-native \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-cluster-demo-dashboard;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "${AGL_APP_REVISION}"

S  = "${WORKDIR}/git"

inherit pkgconfig cmake_qt5 aglwgt

RDEPENDS:${PN} += " \
    qtquickcontrols \
    qtquickcontrols-qmlplugins \
    qtquickcontrols2 \
    qtquickcontrols2-qmlplugins \
    libqtappfw \
    agl-service-signal-composer \
"
