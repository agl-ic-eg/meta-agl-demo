DESCRIPTION = "The set of packages for AGL Speech Subsystem"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-speech-services \
    "

RDEPENDS:${PN} += "\
    agl-service-voice-high \
    agl-service-voice-high-capabilities \
"
