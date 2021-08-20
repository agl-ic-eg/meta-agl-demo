DESCRIPTION = "AGL Telematics Demo Platform image."

require agl-telematics-demo-platform.inc

LICENSE = "MIT"

IMAGE_FEATURES:append = " \
    "

IMAGE_INSTALL:append = " \
    packagegroup-agl-telematics-demo-platform \
    "

