require agl-ivi-image.bb

SUMMARY = "Cross SDK of minimal AGL Distribution for IVI profile"

DESCRIPTION = "SDK image for minimal AGL Distribution for IVI profile. \
It includes the full toolchain, plus development headers and libraries \
to form a standalone cross SDK."

inherit agl-crosssdk

require agl-ivi-crosssdk.inc
