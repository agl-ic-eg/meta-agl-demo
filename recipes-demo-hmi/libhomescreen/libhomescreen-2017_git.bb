SUMMARY     = "AGL Home Screen Library"
DESCRIPTION = "libhomescreen"
HOMEPAGE    = "http://docs.automotivelinux.org"
LICENSE     = "Apache-2.0"
SECTION     = "libs"

BBCLASSEXTEND = " nativesdk"

LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "af-binder json-c"

inherit cmake

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/p/src/libhomescreen.git;protocol=https;branch=${AGL_BRANCH} \
	   file://org.agl.statusbar.conf \
	  "
SRCREV = "de99f924d0e2054b3c7e93562b0d965b3970fd0c"
S = "${WORKDIR}/git"

do_install_append() {
	mkdir -p ${D}${sysconfdir}/dbus-1/session.d
	install -m 0644 ${WORKDIR}/org.agl.statusbar.conf  ${D}${sysconfdir}/dbus-1/session.d
}

RDEPENDS_${PN} = "agl-service-homescreen-2017"
PROVIDES += "virtual/libhomescreen"
