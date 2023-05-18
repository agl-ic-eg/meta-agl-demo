require agl-image-ivi.bb

DESCRIPTION = "AGL Demo Platform image currently contains a simple HMI and \
demos."

require agl-demo-features.inc
require agl-demo-container-guest-integration.inc

# add packages for demo platform (include demo apps) here
IMAGE_INSTALL:append = " \
    packagegroup-agl-demo-platform \
    ${@bb.utils.contains("AGL_FEATURES", "agl-demo-preload", "", "weston-terminal-conf", d)} \
"

