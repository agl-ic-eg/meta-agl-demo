DESCRIPTION = "AGL Cluster Demo Qtwayland Compositor image currently contains a \
simple cluster interface and some AGL service."

LICENSE = "MIT"

require recipes-platform/images/agl-image-boot.inc

IMAGE_FEATURES += "splash package-management ssh-server-dropbear"

inherit features_check

REQUIRED_DISTRO_FEATURES = "wayland"

# add packages for cluster demo qtcompositor
IMAGE_INSTALL:append = " \
    packagegroup-agl-cluster-demo-qtcompositor \
"

# NOTE: This should be revisited after upgrading to hardknott or later,
#       as it may no longer be required.  However, since Wayland is not
#       being used, the new "weston" image feature may not be a viable
#       option.
SYSTEMD_DEFAULT_TARGET = "graphical.target"
