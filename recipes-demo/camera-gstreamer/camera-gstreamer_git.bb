SUMMARY     = "Camera gstreamer demo application"
DESCRIPTION = "AGL demonstration of displaying incoming camera feed"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/camera-gstreamer"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    wayland wayland-native grpc grpc-native \
    gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad \
"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/camera-gstreamer;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "97faa830c65258ba8b60fa53cdbfb3f9d810abb3"

S  = "${WORKDIR}/git"

inherit meson pkgconfig agl-app

AGL_APP_NAME = "Camera"

RDEPENDS:${PN} += " \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
"
