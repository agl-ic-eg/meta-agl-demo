IMAGE_FEATURES =+ "debug-tweaks"

DISTRO_FEATURES:append = " automount "

# convenience
IMAGE_INSTALL:append = " screen mc "

# Include all kernel modules here for ease-of-use during development.
# Note that this is conditional on virtual/kernel not being linux-dummy
# in order to allow building the demo images as containers.
# Comment this out to switch to selecting the kernel modules
# individually.
IMAGE_INSTALL:append = " ${@'kernel-modules' if d.getVar('PREFERRED_PROVIDER_virtual/kernel') != 'linux-dummy' else ''}"

# enable the layer
AGL_FEATURES:append = " agldemo"

# install virtualbox drivers for vmdk
#IMAGE_INSTALL:append = "${@bb.utils.contains_any('IMAGE_FSTYPES', 'wic.vmdk wic.vmdk.xz', ' open-vm-tools vboxguestdrivers', '', d)}"

# Mask out Flutter recipes until meta-flutter has > kirkstone support
BBMASK += "meta-agl-demo/recipes-demo/flutter-"
BBMASK += "meta-agl-demo/recipes-platform/images/.*-flutter.bb"
BBMASK += "meta-agl-demo/recipes-platform/packagegroups/packagegroup-agl-demo-platform-flutter.bb"
