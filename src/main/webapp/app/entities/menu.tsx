import MenuItem from 'app/shared/layout/menus/menu-item';
import React from 'react';
import { Translate } from 'react-jhipster';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/attachment">
        <Translate contentKey="global.menu.entities.attachment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/contact">
        <Translate contentKey="global.menu.entities.contact" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/opportunity">
        <Translate contentKey="global.menu.entities.opportunity" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/nearby-location">
        <Translate contentKey="global.menu.entities.nearbyLocation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/property">
        <Translate contentKey="global.menu.entities.property" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/rental-contract">
        <Translate contentKey="global.menu.entities.rentalContract" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/charge">
        <Translate contentKey="global.menu.entities.charge" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/payment">
        <Translate contentKey="global.menu.entities.payment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer">
        <Translate contentKey="global.menu.entities.customer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/chat-interaction">
        <Translate contentKey="global.menu.entities.chatInteraction" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/notification">
        <Translate contentKey="global.menu.entities.notification" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/quotation">
        <Translate contentKey="global.menu.entities.quotation" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
