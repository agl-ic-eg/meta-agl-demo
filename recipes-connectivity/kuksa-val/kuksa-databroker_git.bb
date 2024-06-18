SUMMARY = "KUKSA.val databroker, the KUKSA Vehicle Abstraction Layer"
#DESCRIPTION = "KUKSA.val provides a COVESA VSS data model describing data in a vehicle."
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
"
 
DEPENDS = "protobuf-native grpc-native"

PV = "0.4.5+git${SRCPV}"

SRC_URI = "git://github.com/eclipse-kuksa/kuksa-databroker.git;protocol=https;branch=main \
           file://0001-Remove-protobuf-src-usage.patch \
           file://kuksa-databroker.service \
"
SRCREV = "8eb7d1a36ccdbec18f742bcecddf8691cb57df46"

require ${BPN}-crates.inc

S = "${WORKDIR}/git"

inherit cargo cargo-update-recipe-crates systemd useradd

# Enable optional VISS support for potential use by e.g. the web apps
CARGO_BUILD_FLAGS += "--features viss"

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

USERADD_PACKAGES = "${PN}"
USERADDEXTENSION = "useradd-staticids"
GROUPADD_PARAM:${PN} = "-g 900 kuksa ;"
USERADD_PARAM:${PN} = "--system -g 900 -u 900 -o -d / --shell /bin/nologin kuksa ;"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}
    fi

    # Install gRPC API protobuf files
    install -d ${D}${includedir}
    cp -dr ${S}/proto/* ${D}${includedir}/
}

PACKAGE_BEFORE_PN += "${PN}-cli"

FILES:${PN} += "${systemd_system_unitdir} ${datadir}"

FILES:${PN}-cli = "${bindir}/databroker-cli"

# The upstream Cargo.toml builds optimized and stripped binaries, for
# now disable the QA check as opposed to tweaking the configuration.
INSANE_SKIP:${PN} = "already-stripped"
INSANE_SKIP:${PN}-cli = "already-stripped"
