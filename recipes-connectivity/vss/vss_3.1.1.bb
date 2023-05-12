SUMMARY = "Vehicle Signal Specification"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

DEPENDS = "vss-tools-native"

inherit allarch update-alternatives

require vss.inc

UPSTREAM_CHECK_GITTAGREGEX = "v(?P<pver>\d+(\.\d+)+)"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"

EXTRA_OEMAKE = "TOOLSDIR=${STAGING_BINDIR_NATIVE}"

do_compile() {
    oe_runmake json
}

do_install() {
    # Cannot use the "install" target in the project Makefile, as it is
    # intended for setting the repo up for builds.
    # For now, just the generated JSON is installed. It is possible that
    # installing the vspec files somewhere as a development package may
    # be useful, but for now things will be kept simple.
    install -d ${D}${datadir}/vss
    install -m 0644 ${S}/vss_rel_${PV}.json ${D}${datadir}/vss/
}

# NOTE:
# A virtual RPROVIDES is not used for now, as packages such as KUKSA.val
# provide their own copies of the VSS JSON, so we can install this one
# and any alternatives in parallel and point e.g. KUKSA.val at the desired
# file with a configuration change. This may be worth revisiting down the
# road.

ALTERNATIVE_LINK_NAME[vss.json] = "${datadir}/vss/vss.json"

ALTERNATIVE:${PN} = "vss.json"
ALTERNATIVE_TARGET_${PN} = "${datadir}/vss/vss_rel_${PV}.json"
