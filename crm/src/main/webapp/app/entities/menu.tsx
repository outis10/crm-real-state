import React, { useEffect } from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';
import { addTranslationSourcePrefix } from 'app/shared/reducers/locale';
import { useAppDispatch, useAppSelector } from 'app/config/store';

const EntitiesMenu = () => {
  const lastChange = useAppSelector(state => state.locale.lastChange);
  const dispatch = useAppDispatch();
  useEffect(() => {
    dispatch(addTranslationSourcePrefix('services/crm/'));
  }, [lastChange]);

  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/crm/customer">
        <Translate contentKey="global.menu.entities.crmCustomer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/crm/contact">
        <Translate contentKey="global.menu.entities.crmContact" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/crm/opportunity">
        <Translate contentKey="global.menu.entities.crmOpportunity" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/crm/quotation">
        <Translate contentKey="global.menu.entities.crmQuotation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/crm/property">
        <Translate contentKey="global.menu.entities.crmProperty" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/crm/rental">
        <Translate contentKey="global.menu.entities.crmRental" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/crm/sale">
        <Translate contentKey="global.menu.entities.crmSale" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/crm/charge">
        <Translate contentKey="global.menu.entities.crmCharge" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/crm/payment">
        <Translate contentKey="global.menu.entities.crmPayment" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
