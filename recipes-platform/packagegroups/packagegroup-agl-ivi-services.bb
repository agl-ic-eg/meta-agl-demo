DESCRIPTION = "The minimal set of services to support AGL IVI demo"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-ivi-services \
    "

RDEPENDS:${PN} += "\
    kuksa-val \
    kuksa-val-agl \
    kuksa-certificates-agl \
    kuksa-dbc-feeder \
    kuksa-vss-init \
    agl-service-hvac \
    agl-service-audiomixer \
    agl-service-radio \
    "
