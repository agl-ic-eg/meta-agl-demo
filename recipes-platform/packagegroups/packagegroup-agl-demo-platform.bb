SUMMARY = "The software for DEMO platform of AGL IVI profile"
DESCRIPTION = "A set of packages belong to AGL Demo Platform"

LICENSE = "MIT"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = "\
    packagegroup-agl-demo-platform \
    "

RDEPENDS:${PN} += "\
    packagegroup-agl-image-ivi \
    packagegroup-agl-kuksa-val-databroker \
    packagegroup-agl-demo \
    "

RDEPENDS:${PN}:append = " \
    weston-ini-conf-no-activate \
    homescreen \
    launcher \
    qtquickcontrols2-agl \
    qtquickcontrols2-agl-style \
    psplash-portrait-config \
    "
