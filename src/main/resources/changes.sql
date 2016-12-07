ALTER TABLE `photon`.`UserGroupRole`
ADD COLUMN `ApproverMail` VARCHAR(255) NULL AFTER `ModifiedBy`;

ALTER TABLE `photon`.`User`
ADD COLUMN `Email` VARCHAR(255) NULL AFTER `ModifiedBy`;
