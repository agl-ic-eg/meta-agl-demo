require agl-image-ivi.bb

DESCRIPTION = "AGL Flutter based Demo Platform image"

require agl-demo-features.inc
require agl-demo-container-guest-integration.inc

# add packages for demo platform (include demo apps) here
IMAGE_INSTALL:append = " \
    packagegroup-agl-demo-platform-flutter \
    weston-ini-conf-flutter \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "", "weston-terminal-conf", d)} \
"

