SUMMARY = "A basic system of AGL distribution of IVI profile"

DESCRIPTION = "Basic image for baseline of AGL Distribution for IVI profile."

LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb

IMAGE_INSTALL += " \
    packagegroup-agl-ivi-connectivity \
    packagegroup-agl-ivi-graphics \
    packagegroup-agl-ivi-multimedia \
    packagegroup-agl-ivi-multimedia-hardware \
    packagegroup-agl-ivi-navigation \
    packagegroup-agl-ivi-identity \
    packagegroup-agl-ivi-services-applaunchd \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-audio", "", "packagegroup-agl-ivi-services-platform", d)} \
    iproute2 \
    "

IMAGE_FEATURES += "splash package-management ssh-server-openssh"


