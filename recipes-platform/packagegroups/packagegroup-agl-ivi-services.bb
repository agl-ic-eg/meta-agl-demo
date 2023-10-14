DESCRIPTION = "The minimal set of services to support AGL IVI demo"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-ivi-services \
    "

RDEPENDS:${PN} += "\
    applaunchd \
    applaunchd-template-agl-app \
    agl-service-hvac \
    agl-service-audiomixer \
    agl-service-radio \
    "
