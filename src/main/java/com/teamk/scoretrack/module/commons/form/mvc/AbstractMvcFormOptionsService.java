package com.teamk.scoretrack.module.commons.form.mvc;

import com.teamk.scoretrack.module.commons.form.IFormOptionsService;

/**
 * Child classes must provide an implementation for {@link AbstractMvcFormOptionsService#prepareFormOptions(Object)}
 * which is used to collect all necessary options for the form getModel. This includes texts from bundles,
 * lists of options, separate objects etc.
 * @author Ivan Krylosov
 */
public abstract class AbstractMvcFormOptionsService<FORM extends MvcForm> implements IFormOptionsService<FORM> {

}
