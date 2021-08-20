SUMMARY = "The software for DEMO platform of AGL HTML5 profile"
DESCRIPTION = "Packages required to demo the HTML5 profile and sample web apps"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-demo-platform-html5 \
    "

ALLOW_EMPTY:${PN} = "1"

RDEPENDS:${PN} += "\
    packagegroup-agl-image-ivi \
    packagegroup-agl-profile-graphical-html5 \
    packagegroup-agl-demo \
    "

AGL_APPS = " \
    html5-homescreen \
    html5-launcher \
    html5-hvac \
    html5-settings \
    html5-mixer \
    html5-mediaplayer \
    html5-dashboard \
    html5-background \
    "

RDEPENDS:${PN}:append = " \
    weston-ini-conf-landscape \
    ${AGL_APPS} \
    "
