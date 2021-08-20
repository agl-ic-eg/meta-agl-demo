SUMMARY = "AGL web runtime packages"
DESCRIPTION = "Specific packages for the AGL web runtime (minus profile-graphical)"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-appfw-html5 \
    "

ALLOW_EMPTY:${PN} = "1"

# add packages for WAM
RDEPENDS:${PN} += " \
    chromium-browser-service \
    wam \
    "
