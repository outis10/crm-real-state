import React, { useEffect } from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';
import { addTranslationSourcePrefix } from 'app/shared/reducers/locale';
import { useAppDispatch, useAppSelector } from 'app/config/store';

const EntitiesMenu = () => {
  const lastChange = useAppSelector(state => state.locale.lastChange);
  const dispatch = useAppDispatch();
  useEffect(() => {
    dispatch(addTranslationSourcePrefix('services/propertymanagement/'));
  }, [lastChange]);

  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/propertymanagement/quotation">
        <Translate contentKey="global.menu.entities.propertyManagementQuotation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/propertymanagement/rental">
        <Translate contentKey="global.menu.entities.propertyManagementRental" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/propertymanagement/sale">
        <Translate contentKey="global.menu.entities.propertyManagementSale" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/propertymanagement/charge">
        <Translate contentKey="global.menu.entities.propertyManagementCharge" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/propertymanagement/payment">
        <Translate contentKey="global.menu.entities.propertyManagementPayment" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
