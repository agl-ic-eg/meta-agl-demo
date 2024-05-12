SUMMARY = "The middlewares for AGL IVI profile"
DESCRIPTION = "The set of packages required by Multimedia Subsystem"
LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-agl-ivi-multimedia \
    packagegroup-agl-ivi-multimedia-client \
    packagegroup-agl-ivi-multimedia-platform \
"

ALLOW_EMPTY:${PN} = "1"

PIPEWIRE_PACKAGES = " \
    packagegroup-pipewire \
    ${@bb.utils.contains('DISTRO_FEATURES', 'agl-devel', 'packagegroup-pipewire-tools alsa-utils', '', d)} \
    wireplumber-config-agl \
    wireplumber-policy-config-agl \
"

RDEPENDS:${PN}-platform += " \
    mpd \
"

RDEPENDS:${PN}-client += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'pipewire', '${PIPEWIRE_PACKAGES}', '', d)} \
    gstreamer1.0-plugins-base-meta \
    gstreamer1.0-plugins-good-meta \
"

RDEPENDS:${PN} += "\
    packagegroup-agl-ivi-multimedia-client \
    packagegroup-agl-ivi-multimedia-platform \
"