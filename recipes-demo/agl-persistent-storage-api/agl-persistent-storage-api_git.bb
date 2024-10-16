DESCRIPTION = "A grpc API that provides persistent storage for the Automotive Grade Linux demo"
SUMMARY = "Our goal is to develop a grpc API for AGLthat serves as persistent storage API for the demo. The API will be written in Rust and make use of tonic for grpc functionality as well as RocksDB as a database backend, using rust-rocksdb. Use cases include retaining settings over a system shutdown (e.g. audio, HVAC, profile data, Wifi settings, radio presets, metric vs imperial units)."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=493c464569a4f93a01fc9025ee76a69b"

SRC_URI = "git://github.com/LSchwiedrzik/agl-persistent-storage-api.git;protocol=https;branch=master \
           file://agl-persistent-storage-api.service \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "de8ecba1279ee2bcb55f0595017165c606fac835"

S = "${WORKDIR}/git"

DEPENDS = "protobuf-native grpc-native rocksdb clang-native"
TOOLCHAIN = "clang"

require ${BPN}-crates.inc

inherit cargo cargo-update-recipe-crates systemd 
#useradd

SYSTEMD_SERVICE:${PN} = "${BPN}.service"

USERADD_PACKAGES = "${PN}"
USERADDEXTENSION = "useradd-staticids"
GROUPADD_PARAM:${PN} = "-g 903 persistent-api ;"
USERADD_PARAM:${PN} = "--system -g 903 -u 903 -o -d / --shell /bin/nologin persistent-api ;"

do_compile:prepend() {
    export ROCKSDB_CXX_STD="--std=c99"
    export ROCKSDB_INCLUDE_DIR="${RECIPE_SYSROOT}/usr/include"

    # Need to set options for the rust-librocksdb-sys crate's bindgen invocation of clang,
    # or there's a good chance it will choke when trying to use the host system's headers.
    export BINDGEN_EXTRA_CLANG_ARGS="${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}"
}

do_install:append () {
    # copy systemd service file into destination folder
    mv ${D}/usr/bin/server ${D}/usr/bin/agl-service-persistent-storage
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}
    fi
}

FILES:${PN} += "${systemd_system_unitdir} ${datadir}"

