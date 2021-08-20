SUMMARY     = "AGL Reference POI application."
DESCRIPTION = "This application provides the function of destination search to AGL.  It uses the API provided by AGL Reference Navigation.  This application uses yelp WebAPI."
HOMEPAGE    = "https://github.com/AGLExport/genivi-navi-yelp-client"
SECTION     = "apps"

LICENSE          = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4ee23c52855c222cba72583d301d2338"

DEPENDS = " \
    qtbase \
    json-c \
    libhomescreen \
    libqtappfw \
"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/poi-yelp;protocol=https;branch=${AGL_BRANCH}"
SRCREV = "${AGL_APP_REVISION}"

S = "${WORKDIR}/git"

inherit cmake_qt5 pkgconfig aglwgt

RDEPENDS:${PN} = "qtbase libqtappfw"
