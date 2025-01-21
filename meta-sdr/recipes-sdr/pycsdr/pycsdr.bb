SUMMARY = "Python bindings for the csdr library "
HOMEPAGE = "https://github.com/luarvique/pycsdr/tree/0.18.27"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
  git://github.com/luarvique/pycsdr.git;protocol=https;branch=master \
"

# Tag 0.18.27
SRCREV = "6ec3e9cdd327b49723a18817a58dfdc9977f6130"

S = "${WORKDIR}/git"

DEPENDS = "python3 csdr"

RDEPENDS:${PN} = "python3 python3-pip python3-setuptools"

inherit setuptools3

do_install() {
    ${STAGING_BINDIR_NATIVE}/python3-native/python3 setup.py install \
        --prefix=${prefix} \
        --root=${D} \
        --install-lib=${PYTHON_SITEPACKAGES_DIR} \
        --install-scripts=${D}${bindir}

    # Clean up unwanted directories
    rm -rf ${D}/${libdir}/python3*/site-packages/pycsdr-0.18.2-py3.10.egg-info
}

FILES:${PN} = " \
    ${libdir}/python3*/site-packages/pycsdr \
"
