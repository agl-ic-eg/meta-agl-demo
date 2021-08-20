SUMMARY     = "Telematics Recorder application"
DESCRIPTION = "AGL demonstration telematics recorder application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/agl-telematics-demo-recorder"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

DEPENDS = "glib-2.0 mosquitto"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-telematics-demo-recorder;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "${AUTOREV}"

S  = "${WORKDIR}/git"

inherit cmake aglwgt

RDEPENDS:${PN} += " \
    agl-service-can-low-level \
    agl-service-gps \
    agl-service-network \
    libmosquitto1 \
"
