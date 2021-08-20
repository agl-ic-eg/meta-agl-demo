SUMMARY = "The software for application framework of AGL IVI profile"
DESCRIPTION = "A set of packages belong to AGL application framework which required by \
Navigation and Location-Based Services Subsystem"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-ivi-navigation \
    "

RDEPENDS:${PN} += "\
    gpsd \
    geoclue \
    ${@bb.utils.contains('AGL_FEATURES', 'agldemo', 'virtual/gpsd-conf', '', d)} \
    ${@bb.utils.contains('AGL_FEATURES', 'agl-refhw-h3', 'gpsd-conf-refhw', '', d)} \
    "
