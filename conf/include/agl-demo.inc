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

# It is not clear these definitions affect image generation in a robust
# way, but define them to both show our intent and quiet the warnings
# that get spammed otherwise.
PREFERRED_RPROVIDER_kuksa-val-certificates-ca = "kuksa-certificates-agl"
PREFERRED_RPROVIDER_kuksa-val-certificates-server = "kuksa-certificates-agl"
PREFERRED_RPROVIDER_kuksa-val-certificates-client = "kuksa-certificates-agl"
