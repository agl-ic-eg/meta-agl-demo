FILESEXTRAPATHS:append := ":${THISDIR}/qtwayland"

# QT_MODULE_BRANCH = "5.4"

# TODO:
#  These patches for IVI-SHELL are tempolary disabled because of issues. And new
#  patches are proposed.
#
#    file://0020-Add-IVI-Shell-protocol-file-version-patch-v6.patch \
#    file://0021-Implement-initial-IVI-Shell-support.patch \
#    file://0001-protocol-update-3rd-party-ivi-application-protocol.patch \
#    file://0002-qwaylandwindow-add-support-for-IVI-Surface-ID-proper.patch \
#

SRC_URI:append = "\
    file://0010-Added-manifest-file-according-to-smack-3-domain-mode.patch \
    file://0001-client-qwaylandintegration-Do-not-attempt-to-use-the.patch \
    "

