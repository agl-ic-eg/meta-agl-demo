require agl-image-ivi.bb

DESCRIPTION = "AGL demo base image"

require agl-demo-features.inc
require agl-demo-container-guest-integration.inc

AGL_DEVEL_INSTALL += "\
    packagegroup-agl-kuksa-val-databroker-devel \
    simple-can-simulator \
    unzip \
    mpc \
"

AGL_APPS_INSTALL = ""

IMAGE_INSTALL += " \
    ${AGL_APPS_INSTALL} \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "${AGL_DEVEL_INSTALL}" , "", d)} \
"



