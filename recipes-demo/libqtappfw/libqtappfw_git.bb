SUMMARY     = "AGL Qt AppFW Library"
DESCRIPTION = "libqtappfw"
HOMEPAGE    = "http://docs.automotivelinux.org"
SECTION     = "libs"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = " \
    qtbase \
    qtbase-native \
    qtdeclarative \
    qtwebsockets \
    glib-2.0 \
    bluez-glib \
    connman-glib \
    libmpdclient \
    protobuf-native \
    grpc-native \
    protobuf \
    grpc \
    kuksa-databroker \
"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/libqtappfw;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "4694b211569e15dc8aa4e75b6fb70e75946e766d"
S       = "${WORKDIR}/git"

# PV needs to be modified with SRCPV to work AUTOREV correctly
PV = "2.0.1+git${SRCPV}"

inherit meson pkgconfig

EXTRA_OEMESON += "-Dprotos=${STAGING_INCDIR}"

RRECOMMENDS:${PN} += " \
    bluez5 \
    connman \
    ${@bb.utils.contains('AGL_FEATURES', 'agl-kvm-host-audio', '', 'mpd', d)} \
"

BBCLASSEXTEND = "nativesdk"
