SUMMARY = "Open source, multi-user SDR receiver software with a web interface"
HOMEPAGE = "https://github.com/luarvique/openwebrx/tree/1.2.75"

LICENSE = "AGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=73f1eb20517c55bf9493b7dd6e480788"

SRC_URI = " \
  git://github.com/luarvique/openwebrx.git;protocol=https;branch=master \
"

# Tag 1.2.75
SRCREV = "72af96089d1d11d27b76a1ebf268b742b445aa68"

S = "${WORKDIR}/git"

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE:${PN} = "openwebrx.service"

DEPENDS = "python3"

# TODO: depend on one of the three SDR programs

inherit setuptools3

do_install() {
    install -d ${D}${bindir}

    install -m 0755 ${S}/openwebrx.py ${D}${bindir}/openwebrx

    ${STAGING_BINDIR_NATIVE}/python3-native/python3 setup.py install \
        --prefix=${prefix} \
        --root=${D} \
        --install-lib=${PYTHON_SITEPACKAGES_DIR} \
        --install-scripts=${D}${bindir}

    # Replace shebang to use target Python
    sed -i -e '1s|^#!.*|#!/usr/bin/env python3|' ${D}${bindir}/openwebrx

    # Clean up unwanted directories
    rm -rf ${D}/build
    rm -rf ${D}/${libdir}/python3*/site-packages/OpenWebRX-1.2.75-py3.10.egg-info

    install -d ${D}${sysconfdir}/openwebrx

    cp -r ${S}/bands.json ${D}${sysconfdir}/openwebrx/
    cp -r ${S}/bands-r*.json ${D}${sysconfdir}/openwebrx/
    cp -r ${S}/bookmarks.json ${D}${sysconfdir}/openwebrx/
    cp -r ${S}/bookmarks.d ${D}${sysconfdir}/openwebrx/
    cp -r ${S}/openwebrx.conf ${D}${sysconfdir}/openwebrx/

    install -d ${D}/${localstatedir}/lib/openwebrx
    
    install -d ${D}/${systemd_unitdir}/system
    install -m 0644 ${S}/systemd/openwebrx.service ${D}/${systemd_unitdir}/system
}

PACKAGES += "${PN}-csdr ${PN}-htdocs ${PN}-owrx"

FILES:${PN} = " \
    ${bindir}/openwebrx \
    ${sysconfdir}/openwebrx \
    ${localstatedir}/lib/openwebrx \
"

FILES:${PN}-csdr = " \
    ${libdir}/python3*/site-packages/csdr \
"

FILES:${PN}-htdocs = " \
    ${libdir}/python3*/site-packages/htdocs \
"

FILES:${PN}-owrx = " \
    ${libdir}/python3*/site-packages/owrx \
"

RDEPENDS:${PN} = " \
    python3 \
    pycsdr \
    owrx-connector \
    ${PN}-csdr \
    ${PN}-htdocs \
    ${PN}-owrx \
"

inherit useradd
USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--system -d /var/lib/openwebrx -m -s /bin/nologin -G plugdev openwebrx"
