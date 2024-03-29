package softwarePackage;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.PackageFileDAO;
import dao.SoftwarePackageDAO;
import entities.PackageFile;
import entities.SoftwarePackage;

@Named
@RequestScoped
public class SoftwarePackageListBB {
	private List<SoftwarePackage> softwarePackageList;

	@Inject
	SoftwarePackageDAO softPackDAO;
	
	@Inject
	PackageFileDAO pfDAO;
	
	
	@PostConstruct
    public void init() {
		softwarePackageList = softPackDAO.getFullList();
   
    }
	
	public long countPackages() {
		return softPackDAO.countPackages();
	}
	

	public List<SoftwarePackage> getSoftwarePackageList() {
		return softwarePackageList;
	}

	public void setSoftwarePackageList(List<SoftwarePackage> softwarePackageList) {
		this.softwarePackageList = softwarePackageList;
	}
	
	
	
	
}