SUMMARY = "Modular audio codec server ."

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
    git://github.com/jketterl/codecserver.git;protocol=https;branch=develop \
    file://0001-Add-support-for-CMake-package-config-files.patch \
"
SRCREV = "487f6ca3e8e411c1689aa6e0e2c0db96e0a71349"

S = "${WORKDIR}/git"

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "codecserver.service"

inherit cmake pkgconfig

DEPENDS = "protobuf protobuf-native udev"

RDEPENDS:${PN} = "udev"

do_install:append() {
    install -d ${D}/${sysconfdir}/codecserver/
    cp -r ${S}/conf/codecserver.conf ${D}${sysconfdir}/codecserver/

    # Package's install script puts systemd files here, but we don't want that.
    rm -rf ${D}/lib

    # We ignore the ambe3k driver that is dropped in this folder for now
    rm -rf ${D}/${libdir}/codecserver

    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${S}/systemd/codecserver.service ${D}/${systemd_unitdir}/system
}

FILES:${PN} = " \
    ${bindir}/codecserver \
    ${libdir}/libcodecserver.so* \
    ${sysconfdir}/codecserver \
    ${systemd_unitdir}/system/codecserver.service \
"

FILES:${PN}-dev += " \
    ${libdir}/cmake/CodecServer \
    ${includedir} \
"

inherit useradd
USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--system -d /var/lib/codecserver -m -s /bin/nologin codecserver"
