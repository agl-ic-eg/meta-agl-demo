SUMMARY = "AGL Telematics Demo Platform image."
LICENSE = "MIT"

require recipes-platform/images/agl-image-minimal.bb

inherit features_check

REQUIRED_DISTRO_FEATURES = "3g"

IMAGE_INSTALL += " \
    packagegroup-agl-telematics-demo-platform \
"
