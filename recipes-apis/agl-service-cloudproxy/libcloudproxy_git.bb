SUMMARY     = "AGL Client Cloud Proxy Library"
DESCRIPTION = "libcloudproxy"
HOMEPAGE    = "http://docs.automotivelinux.org"
LICENSE     = "Apache-2.0"
SECTION     = "libs"

BBCLASSEXTEND = " nativesdk"

LIC_FILES_CHKSUM = "file://LICENSE;md5=ae6497158920d9524cf208c09cc4c984"

DEPENDS = "af-binder json-c"

inherit cmake

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/src/libcloudproxy;protocol=https;branch=master"
SRCREV = "2d66933b9bf25af66696b097283109c8a6e87151"

S = "${WORKDIR}/git"

