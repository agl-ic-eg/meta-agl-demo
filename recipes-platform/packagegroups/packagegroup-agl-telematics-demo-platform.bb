SUMMARY = "The software for AGL telematics profile demo platform"
DESCRIPTION = "A set of packages belonging to the AGL telematics demo platform"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-telematics-demo-platform \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-agl-profile-telematics \
    "

AGL_APPS = " \
    telematics-recorder \
    "

AGL_APIS = " \
    agl-service-gps \
    "

RDEPENDS:${PN}:append = " \
    gpsd \
    sw-gpsd-udev-conf \
    usb-can-udev-conf \
    simple-can-simulator \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'ofono-tests gps-utils' , '', d)} \
    ${AGL_APPS} \
    ${AGL_APIS} \
"
# packagegroup-agl-core-navigation? (brings in geoclue...)
