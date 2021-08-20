SUMMARY     = "AGL cloud proxy service"
DESCRIPTION = "AGL cloud proxy service build with recipe method"
HOMEPAGE    = "https://gerrit.automotivelinux.org/gerrit/apps/agl-service-cloudproxy"
SECTION     = "apps"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=34f8c1142fd6208a8be89399cb521df9"

SRC_URI = "git://gerrit.automotivelinux.org/gerrit/apps/agl-service-cloudproxy;protocol=https;branch=${AGL_BRANCH}"
SRCREV = "${AGL_APP_REVISION}"

PV      = "1.0+git${SRCPV}"
S       = "${WORKDIR}/git"

DEPENDS = "azure-iot-sdk-c aws-iot-device-sdk-embedded-c glib-2.0 nlohmann-json"

inherit cmake aglwgt pkgconfig

# Azure include files
CXXFLAGS:prepend += "-I${STAGING_INCDIR}/azureiot"

RDEPENDS:${PN} += "azure-iot-sdk-c azure-c-shared-utility aws-iot-device-sdk-embedded-c"

BBCLASSEXTEND = "native nativesdk"
