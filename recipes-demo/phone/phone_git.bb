SUMMARY     = "Phone application"
DESCRIPTION = "AGL demonstration Phone application"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/#/admin/projects/apps/phone"
SECTION     = "apps"

LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "qtquickcontrols2 libqtappfw gstreamer1.0"

PV = "1.0+git${SRCPV}"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/phone;protocol=https;branch=${AGL_BRANCH}"
SRCREV  = "067f1e0b7b1454922e09efada608410cd3b955b0"

S = "${WORKDIR}/git"

inherit qmake5

FILES:${PN} += "${datadir}/icons/ ${datadir}/sounds/"

RDEPENDS:${PN} += "libqtappfw"
