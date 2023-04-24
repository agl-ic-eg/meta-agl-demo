SUMMARY = "KUKSA.val, the KUKSA Vehicle Abstraction Layer"
DESCRIPTION = "KUKSA.val provides a COVESA VSS data model describing data in a vehicle."
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "Apache-2.0 & BSL-1.0 & MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9 \
                    file://3rd-party-libs/jsoncons/LICENSE;md5=6ee7f7ed2001e4cde4679fdb8926f820 \
                    file://3rd-party-libs/turtle/LICENSE_1_0.txt;md5=e4224ccaecb14d942c71d31bef20d78c \
                    file://3rd-party-libs/jwt-cpp/LICENSE;md5=8325a5ce4414c65ffdda392e0d96a9ff"

DEPENDS = "boost openssl mosquitto protobuf-native grpc-native grpc"

require kuksa-val.inc

SRC_URI += "file://kuksa-val.service \
            file://0001-Make-Boost-requirements-more-liberal.patch;patchdir=.. \
            file://0002-Fix-gRPC-configuration-for-OE-cross-compiling.patch;patchdir=.. \
            file://0003-Make-install-locations-configurable.patch;patchdir=.. \
            file://0004-Disable-default-fetch-and-build-of-googletest.patch;patchdir=.. \
            file://0005-kuksa-val-server-Add-missing-check_git-dependency.patch;patchdir=.. \
"

S = "${WORKDIR}/git/kuksa-val-server"

inherit cmake pkgconfig systemd useradd

SYSTEMD_SERVICE:${PN} = "kuksa-val.service"

USERADD_PACKAGES = "${PN} ${PN}-server-certificates"
USERADDEXTENSION = "useradd-staticids"
GROUPADD_PARAM:${PN} = "-g 900 kuksa ;"
USERADD_PARAM:${PN} = "--system -g 900 -u 900 -o -d / --shell /bin/nologin kuksa ;"
GROUPADD_PARAM:${PN}-server-certificates = "-g 900 kuksa ;"

# Configure file locations more along the lines of FHS instead of kuksa.val's
# default locations.
EXTRA_OECMAKE = " \
    -DKUKSA_INSTALL_BINDIR=${bindir} \
    -DKUKSA_INSTALL_CERTDIR=${sysconfdir}/kuksa-val \
    -DKUKSA_INSTALL_DATADIR=${datadir}/kuksa-val \
    -DKUKSA_INSTALL_CONFIGDIR=${sysconfdir}/kuksa-val \
"

do_install:append() {
    # Lower the logging level used in the installed config.ini from the upstream
    # default of "ALL", which spams the logs.
    sed -i 's/^log-level = .*/log-level = WARNING/' ${D}/${sysconfdir}/kuksa-val/config.ini

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/kuksa-val.service ${D}${systemd_system_unitdir}
    fi
}

# Put certificates into their own packages so we can avoid duplicates
# of them for e.g. cluster clients, and so downstream users can
# replace them with their own certificates.
#
# NOTE:
#   Downstream users can replace these packages with alternates by
#   having their packages set their RPROVIDES to include the desired
#   kuksa-val-certificates-* and explicitly adding their package(s)
#   to an image, they will end up getting priority during rootfs
#   construction and installed instead of the default ones here.

PACKAGE_BEFORE_PN += "${PN}-certificates-ca ${PN}-certificates-server ${PN}-certificates-client"

FILES:${PN}-certificates-ca = " \
    ${sysconfdir}/kuksa-val/CA.pem \
"

FILES:${PN}-certificates-server = " \
    ${sysconfdir}/kuksa-val/Server.key \
    ${sysconfdir}/kuksa-val/Server.pem \
"
RDEPENDS:${PN}-certificates-server += "${PN}-certificates-ca"

FILES:${PN}-certificates-client = " \
    ${sysconfdir}/kuksa-val/Client.key \
    ${sysconfdir}/kuksa-val/Client.pem \
"
RDEPENDS:${PN}-certificate-clients += "${PN}-certificates-ca"

FILES:${PN} += "${systemd_system_unitdir} ${datadir}"

RDEPENDS:${PN} += "${PN}-certificates-server ${PN}-certificates-client"
