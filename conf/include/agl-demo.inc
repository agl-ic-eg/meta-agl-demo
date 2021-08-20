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

# Extend agl-voiceagent-alexa image feature with local alexa-viewer
# package.
FEATURE_PACKAGES_agl-voiceagent-alexa:append = " alexa-viewer"

# enable the layer
AGL_FEATURES:append = " agldemo"

# Set preferred provider for low-can device mapping configuration
PREFERRED_RPROVIDER_virtual/low-can-dev-mapping ?= "${@bb.utils.contains('DISTRO_FEATURES', 'AGLCI', 'dev-mapping-ci', 'dev-mapping', d)}"

# voice api inclusion
PREFERRED_RPROVIDER_virtual/voice-high-config = "default-voice-high-config"
FEATURE_PACKAGES_agl-speech-framework = "packagegroup-agl-speech-services"

# install virtualbox drivers for vmdk
IMAGE_INSTALL:append = "${@bb.utils.contains_any('IMAGE_FSTYPES', 'wic.vmdk wic.vmdk.xz', ' open-vm-tools vboxguestdrivers', '', d)}"
