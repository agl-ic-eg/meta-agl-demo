SUMMARY = "KUKSA.val, the KUKSA Vehicle Abstraction Layer"
DESCRIPTION = "KUKSA.val provides a COVESA VSS data model describing data in a vehicle."
HOMEPAGE = "https://github.com/eclipse/kuksa.val"
BUGTRACKER = "https://github.com/eclipse/kuksa.val/issues"

LICENSE = "Apache-2.0 & BSL-1.0 & MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9 \
                    file://3rd-party-libs/jsoncons/LICENSE;md5=6ee7f7ed2001e4cde4679fdb8926f820 \
                    file://3rd-party-libs/turtle/LICENSE_1_0.txt;md5=e4224ccaecb14d942c71d31bef20d78c \
                    file://3rd-party-libs/jwt-cpp/LICENSE;md5=8325a5ce4414c65ffdda392e0d96a9ff"

DEPENDS = "boost openssl mosquitto nss protobuf-native grpc-native grpc"

require kuksa-val.inc

SRC_URI += "file://kuksa-val.service \
            file://0001-Make-Boost-requirements-more-liberal.patch;striplevel=2 \
            file://0002-Fix-gRPC-configuration-for-OE-cross-compiling.patch;striplevel=2 \
            file://0003-Make-install-locations-configurable.patch;striplevel=2 \
            file://0004-Disable-default-fetch-and-build-of-googletest.patch;striplevel=2 \
            file://0005-kuksa-val-server-Add-missing-check_git-dependency.patch;striplevel=2 \
            file://Server.key \
            file://Server.pem \
"
# NOTE: Ideally this would be applied, but our S definition makes it problematic:
#   file://0001-genCerts.sh-add-Subject-Alt-Name-extension-to-server.patch;striplevel=?
#

S = "${WORKDIR}/git/kuksa-val-server"

inherit cmake pkgconfig systemd useradd

SYSTEMD_SERVICE:${PN} = "kuksa-val.service"

USERADD_PACKAGES = "${PN}"
USERADDEXTENSION = "useradd-staticids"
GROUPADD_PARAM:${PN} = "-g 900 kuksa ;"
USERADD_PARAM:${PN} = "--system -g 900 -u 900 -o -d / --shell /bin/nologin kuksa ;"

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

    # Install replacement server key + certificate
    # These are AGL specific versions generated using a tweaked
    # genCerts.sh script from the source tree that adds the now
    # required subjectAltName extension field to make python3-ssl
    # happy.  This will be addressed with upstream and can hopefully
    # be dropped in the future.
    rm -f ${D}${sysconfdir}/kuksa-val/Server.key
    install ${WORKDIR}/Server.key ${D}${sysconfdir}/kuksa-val/
    rm -f ${D}${sysconfdir}/kuksa-val/Server.pem
    install ${WORKDIR}/Server.pem ${D}${sysconfdir}/kuksa-val/

    # Restrict server certificate access
    # NOTE: The client certificates are left alone here for client
    #       development convenience for now, but this will need to
    #       be revisited.
    chmod 640 ${D}${sysconfdir}/kuksa-val/Server.key
    chgrp 900 ${D}${sysconfdir}/kuksa-val/Server.key
    chmod 640 ${D}${sysconfdir}/kuksa-val/Server.pem
    chgrp 900 ${D}${sysconfdir}/kuksa-val/Server.pem
}

pkg_postinst_ontarget:${PN}-client-certificates () {
    certutil -A -d /home/agl-driver/.pki/nssdb -n "KuksaRootCA" -t "pC,," -i ${sysconfdir}/kuksa-val/CA.pem
    chown agl-driver:agl-driver -R /home/agl-driver/
}

# Put client certificates into their own package so we can avoid
# duplicates of them for e.g. cluster clients.  Longer term this
# will need to be revisited.
PACKAGE_BEFORE_PN += "${PN}-client-certificates"

FILES:${PN}-client-certificates = " \
    ${sysconfdir}/kuksa-val/Client.key \
    ${sysconfdir}/kuksa-val/Client.pem \
    ${sysconfdir}/kuksa-val/CA.pem \
"

FILES:${PN} += "${systemd_system_unitdir} ${datadir}"

RDEPENDS:${PN} += "${PN}-client-certificates nss-agl-driver-db"
