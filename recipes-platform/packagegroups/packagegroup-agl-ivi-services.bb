DESCRIPTION = "The minimal set of services to support AGL IVI demo"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-ivi-services \
    packagegroup-agl-ivi-services-platform \
    packagegroup-agl-ivi-services-applaunchd \
"

RDEPENDS:${PN}-applaunchd += " \
    applaunchd \
    applaunchd-template-agl-app \
"

RDEPENDS:${PN}-platform += " \
    agl-service-hvac \
    agl-service-audiomixer \
    agl-service-radio \
    mpd \
"

RDEPENDS:${PN} += " \
    packagegroup-agl-ivi-services-applaunchd \
    packagegroup-agl-ivi-services-platform \
"
