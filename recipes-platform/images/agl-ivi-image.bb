SUMMARY = "AGL IVI demo base image"
LICENSE = "MIT"

require recipes-platform/images/agl-image-compositor.bb

require agl-ivi-demo-features.inc
require agl-demo-container-guest-integration.inc

IMAGE_FEATURES += "splash package-management ssh-server-openssh"

AGL_DEVEL_INSTALL += "\
    simple-can-simulator \
    unzip \
    mpc \
"

AGL_APPS_INSTALL = ""

IMAGE_INSTALL += " \
    packagegroup-agl-ivi-connectivity \
    packagegroup-agl-ivi-graphics \
    packagegroup-agl-ivi-multimedia \
    packagegroup-agl-ivi-multimedia-hardware \
    packagegroup-agl-ivi-navigation \
    packagegroup-agl-ivi-identity \
    packagegroup-agl-ivi-services-applaunchd \
    iproute2 \
    ${AGL_APPS_INSTALL} \
    ${@bb.utils.contains("DISTRO_FEATURES", "agl-devel", "${AGL_DEVEL_INSTALL}" , "", d)} \
    ${@bb.utils.contains("AGL_FEATURES", "agl-kvm-host-audio", "", "packagegroup-agl-ivi-services-platform", d)} \
"

