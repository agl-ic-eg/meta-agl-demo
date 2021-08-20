FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://psplash-colors.h"

SPLASH_IMAGES = "file://psplash-poky-img.h;outsuffix=default"

do_configure:append () {
	cp -f ${WORKDIR}/psplash-colors.h ${S}
}
