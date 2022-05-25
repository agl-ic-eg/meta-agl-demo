FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://0001-cmake-add-separate-export-for-plugin-targets.patch"

do_install:append() {
    # The gRPC package version seems to get clobbered in its CMake
    # package (seemingly with the c-ares version), but the root cause
    # is not obvious and the issue is not present with newer versions
    # of things in kirkstone.  So for now just fix the version to
    # avoid mismatch errors.
    sed -i "s/^set(PACKAGE_VERSION \"1\.16\.1\")$/set(PACKAGE_VERSION \"${PV}\")/" ${D}/${libdir}/cmake/${BPN}/gRPCConfigVersion.cmake
}
